package com.sivalabs.techbuzz.posts.usecases.deletepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePostHandler {

	private final PostRepository postRepository;

	public void deletePost(Long postId) {
		Post post = getPost(postId);
		postRepository.delete(post);
	}

	public Post getPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
	}

}
