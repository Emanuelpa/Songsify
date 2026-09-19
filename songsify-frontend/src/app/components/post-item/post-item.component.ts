import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Post } from '../../models/post.model';

@Component({
  selector: 'app-post-item',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})
export class PostItemComponent {
  @Input({ required: true }) post!: Post;
  @Output() delete = new EventEmitter<number>();

  onDeleteClick(): void {
    this.delete.emit(this.post.id!);
  }
}