package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.TrainingDto;

/**
 * Komponent odpowiedzialny za mapowanie między encją {@link Training} a jej reprezentacją DTO {@link TrainingDto}.
 * Umożliwia konwersję danych między warstwą bazodanową a warstwą prezentacji.
 */
@Component
public class TrainingMapper {

    /**
     * Mapuje encję {@link Training} na obiekt DTO {@link TrainingDto}.
     *
     * @param training encja treningu do zmapowania
     * @return obiekt {@link TrainingDto} zawierający dane z encji
     */
    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    /**
     * Mapuje obiekt DTO {@link TrainingDto} na encję {@link Training}.
     * Używane m.in. do zapisu lub aktualizacji danych w bazie.
     *
     * @param trainingDto obiekt DTO do zmapowania
     * @return nowa instancja encji {@link Training}
     */
    Training toEntity(TrainingDto trainingDto) {
        return new Training(trainingDto.user(),
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.id());
    }
}
