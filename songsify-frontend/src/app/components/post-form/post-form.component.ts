import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { RouterLink, ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Post } from '../../models/post.model';
import { PostService } from '../../services/post.service';
import { extractErrorMessage } from '../../utils/error-handler';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.css'
})
export class PostFormComponent implements OnInit {
  postForm = new FormGroup({
    title: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    author: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    songName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    singerName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    songUrl: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    description: new FormControl('', { nonNullable: true, validators: [Validators.maxLength(1000)] })
  });

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
        this.postForm.setValue({
          title: data.title,
          author: data.author,
          songName: data.songName,
          singerName: data.singerName,
          songUrl: data.songUrl,
          description: data.description
        });
      },
      error: (err: HttpErrorResponse) => {
        this.loadError = extractErrorMessage(err);
      }
    });
  }

  savePost(): void {
    if (this.postForm.invalid) {
      this.postForm.markAllAsTouched();
      return;
    }

    const post: Post = this.postForm.getRawValue();

    if (this.isEditMode && this.postId) {
      this.postService.update(this.postId, post).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = extractErrorMessage(err);
        }
      });
    } else {
      this.postService.create(post).subscribe({
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