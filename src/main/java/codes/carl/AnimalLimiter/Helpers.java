package codes.carl.AnimalLimiter;

import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;

import java.util.List;

class Helpers {

    static boolean checkTooManyAnimalsNearby(Location location, final int RANGE, final int MAX_ALLOWED_ANIMALS) {

        List<Entity> nearbyEntities = (List<Entity>) location.getWorld().getNearbyEntities(location, RANGE, RANGE, RANGE);

        int animalCount = 0;
        int nearbyEntitySize = nearbyEntities.size();

        // If not enough entities nearby just shortcut.
        if (nearbyEntitySize < MAX_ALLOWED_ANIMALS)
            return false;

        int i = 0;
        while (i < nearbyEntitySize) {

            if (nearbyEntities.get(i) instanceof Animals) {
                animalCount++;
            }

            // Shortcut if not enough entities left to check that could be over the limit.
            int remainingEntities = nearbyEntitySize - (i + 1);
            if ((MAX_ALLOWED_ANIMALS - animalCount) > remainingEntities)
                return false;

            // If we reach more than max allowed animals then shortcut exit the event and cancel.
            if (animalCount > MAX_ALLOWED_ANIMALS) {
                return true;
            }

            i++;
        }

        return false;
    }
}
