package codes.carl.AnimalLimiter;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

import static codes.carl.AnimalLimiter.Helpers.checkTooManyAnimalsNearby;

public class BreedLimiter implements Listener {

    private static final int RANGE = 30;
    private static final int MAX_ALLOWED_ANIMALS = 30;

    @EventHandler
    public void onBreed(EntityBreedEvent entityBreedEvent) {

        LivingEntity breeder = entityBreedEvent.getBreeder();

        if (breeder instanceof Player) {
            Player player = (Player) entityBreedEvent.getBreeder();

            if (checkTooManyAnimalsNearby(player.getLocation(), RANGE, MAX_ALLOWED_ANIMALS)) {
                cancelBreeding(entityBreedEvent, player);
            }
        }
    }

    private void cancelBreeding(final EntityBreedEvent entityBreedEvent, Player player) {

        // Cancel the breeding event
        entityBreedEvent.setCancelled(true);

        final Ageable mother = (Ageable) entityBreedEvent.getMother();
        final Ageable father = (Ageable) entityBreedEvent.getFather();

        // Stop breeding
        mother.setBreed(false);
        father.setBreed(false);

        // Allow the animals to breed again after 5 seconds.
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                mother.setBreed(true);
                father.setBreed(true);
            }
        }, 100L);

        // Give the player their item back
        ItemStack bredItem = entityBreedEvent.getBredWith();
        player.getInventory().addItem(bredItem);

        // Show a warning to the player
        player.sendMessage("Sorry too many animals nearby!");
    }

}