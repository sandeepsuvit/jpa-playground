package com.sivalabs.techbuzz.posts.usecases.createvote;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.entities.Votes;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteHandler {

	private final PostRepository postRepository;

	public void addVote(CreateVoteRequest request) {
		Post post = postRepository.findById(request.postId()).orElseThrow();
		Integer value = request.value();
		Votes votes = post.getVotes();
		if(votes == null) {
			votes = new Votes();
		}
		if( value > 0) {
			votes.setUp(votes.getUp() == null ? 1 : votes.getUp() + 1);
		} else {
			votes.setDown(votes.getDown() == null ? 1 : votes.getDown() + 1);
		}
		post.setVotes(votes);
		postRepository.save(post);
		log.info("Vote update successfully");
	}

}
