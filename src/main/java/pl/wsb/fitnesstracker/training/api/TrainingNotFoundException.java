package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Wyjątek wskazujący, że nie odnaleziono treningu.
 * <p>
 * Ten wyjątek powinien być rzucany w sytuacjach, gdy użytkownik próbuje uzyskać dostęp
 * do treningu, który nie istnieje w systemie (np. po ID).
 * </p>
 *
 * Dziedziczy po klasie {@link NotFoundException}, dzięki czemu może być obsługiwany
 * w spójny sposób przez globalny handler wyjątków.
 */
@SuppressWarnings("squid:S110")
public class TrainingNotFoundException extends NotFoundException {

    /**
     * Tworzy wyjątek z niestandardowym komunikatem.
     *
     * @param message treść komunikatu błędu
     */
    private TrainingNotFoundException(String message) {
        super(message);
    }

    /**
     * Tworzy wyjątek dla przypadku, gdy nie znaleziono treningu o podanym ID.
     *
     * @param id identyfikator treningu
     */
    public TrainingNotFoundException(Long id) {
        this("Training with ID=%s was not found".formatted(id));
    }

}
