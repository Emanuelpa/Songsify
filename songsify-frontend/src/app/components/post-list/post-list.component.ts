import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Post } from '../../models/post.model';
import { PostService } from '../../services/post.service';
import { PostItemComponent } from '../post-item/post-item.component';
import { extractErrorMessage } from '../../utils/error-handler';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, RouterLink, PostItemComponent],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {
  posts: Post[] = [];
  errorMessage: string = '';
  loading: boolean = true;

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.loading = true;
    this.postService.findAll().subscribe({
      next: (data) => {
        this.posts = data;
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = extractErrorMessage(err);
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
        this.loadPosts();
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = extractErrorMessage(err);
      }
    });
  }
}