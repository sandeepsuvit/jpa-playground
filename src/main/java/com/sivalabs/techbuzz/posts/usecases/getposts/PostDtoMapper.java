package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.entities.Votes;
import org.springframework.stereotype.Component;

@Component
public class PostDtoMapper {

	public PostDTO toDTO(Post post) {
		String category = null;
		if (post.getCategory() != null) {
			category = post.getCategory().getName();
		}
		int upVotes = 0;
		int downVotes = 0;

		Votes votes = post.getVotes();
		if (votes != null) {
			upVotes = votes.getUp() != null ? votes.getUp() : 0;
			downVotes = votes.getDown() != null ? votes.getDown() : 0;
		}
		return new PostDTO(post.getId(), post.getTitle(), post.getUrl(), post.getContent(), category, upVotes, downVotes,
				post.getCreatedAt(), post.getUpdatedAt());
	}

}
