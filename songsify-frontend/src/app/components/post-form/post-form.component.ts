import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../models/post.model';
import { PostService } from '../../services/post.service';
import { extractErrorMessage } from '../../utils/error-handler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.css'
})
export class PostFormComponent implements OnInit {
  post: Post = {
    title: '',
    author: '',
    songName: '',
    singerName: '',
    songUrl: '',
    description: ''
  };

  postId: number | null = null;
  isEditMode: boolean = false;
  errorMessage: string = '';
  loadError: string = '';

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.postId = Number(idParam);
      this.isEditMode = true;
      this.loadPost(this.postId);
    }
  }

  loadPost(id: number): void {
    this.postService.findById(id).subscribe({
      next: (data) => {
        this.post = data;
      },
      error: (err: HttpErrorResponse) => {
        this.loadError = extractErrorMessage(err);
      }
    });
  }

  savePost(): void {
    if (this.isEditMode && this.postId) {
      this.postService.update(this.postId, this.post).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.errorMessage = extractErrorMessage(err);
        }
      });
    } else {
      this.postService.create(this.post).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = extractErrorMessage(err);
        }
      });
    }
  }
}