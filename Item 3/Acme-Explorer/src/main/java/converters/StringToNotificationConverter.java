package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.NotificationRepository;
import domain.Notification;

@Component
@Transactional
public class StringToNotificationConverter implements
		Converter<String, Notification> {

	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public Notification convert(final String text) {
		Notification result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.notificationRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
