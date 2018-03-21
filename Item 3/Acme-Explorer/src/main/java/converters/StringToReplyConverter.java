package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReplyRepository;
import domain.Reply;

@Component
@Transactional
public class StringToReplyConverter implements Converter<String, Reply> {

	@Autowired
	ReplyRepository	replyRepository;


	@Override
	public Reply convert(final String text) {
		Reply result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.replyRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
