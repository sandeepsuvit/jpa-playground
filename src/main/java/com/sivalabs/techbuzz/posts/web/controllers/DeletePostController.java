package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.deletepost.DeletePostHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequiredArgsConstructor
public class DeletePostController {

	private final DeletePostHandler deletePostHandler;

	@DeleteMapping("/posts/{id}")
	@ResponseStatus
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		deletePostHandler.deletePost(id);
		return ResponseEntity.ok().build();
	}
}
