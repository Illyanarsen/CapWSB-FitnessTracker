package pl.wsb.fitnesstracker.training.internal;

/**
 * Enum reprezentujący możliwe typy aktywności fizycznej w systemie FitnessTracker.
 * <p>
 * Każdy typ posiada nazwę wyświetlaną, która może być używana np. w interfejsie użytkownika.
 * </p>
 *
 * Dostępne typy aktywności:
 * <ul>
 *     <li>{@link #RUNNING} – bieganie</li>
 *     <li>{@link #CYCLING} – jazda na rowerze</li>
 *     <li>{@link #WALKING} – chodzenie</li>
 *     <li>{@link #SWIMMING} – pływanie</li>
 *     <li>{@link #TENNIS} – tenis</li>
 * </ul>
 */
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Zwraca przyjazną nazwę aktywności do wyświetlania.
     *
     * @return nazwa aktywności
     */
    public String getDisplayName() {
        return displayName;
    }

}
