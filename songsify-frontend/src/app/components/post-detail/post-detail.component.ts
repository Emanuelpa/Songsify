import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Post } from '../../models/post.model';
import { PostService } from '../../services/post.service';
import { PostItemComponent } from '../post-item/post-item.component';
import { extractErrorMessage } from '../../utils/error-handler';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, PostItemComponent],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css'
})
export class PostDetailComponent implements OnInit {
  post: Post | null = null;
  loadError: string = '';
  loading: boolean = true;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);
    this.loadPost(id);
  }

  loadPost(id: number): void {
    this.loading = true;
    this.postService.findById(id).subscribe({
      next: (data) => {
        this.post = data;
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.loadError = extractErrorMessage(err);
        this.loading = false;
      }
    });
  }

  onDeletePost(id: number): void {
    const confirmado = confirm('¿Seguro que quieres eliminar esta recomendación? Esta acción no se puede deshacer.');

    if (!confirmado) {
      return;
    }

    this.postService.delete(id).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (err: HttpErrorResponse) => {
        this.loadError = extractErrorMessage(err);
      }
    });
  }
}