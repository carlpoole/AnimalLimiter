package codes.carl.AnimalLimiter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

import static codes.carl.AnimalLimiter.Helpers.checkTooManyAnimalsNearby;

public class EggLimiter implements Listener {

    private static final int RANGE = 30;
    private static final int MAX_ALLOWED_ANIMALS = 30;

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent playerEggThrowEvent) {

        Player player = playerEggThrowEvent.getPlayer();

        if (checkTooManyAnimalsNearby(player.getLocation(), RANGE, MAX_ALLOWED_ANIMALS)) {
            cancelEggThrow(playerEggThrowEvent, player);
        }

    }

    private void cancelEggThrow(PlayerEggThrowEvent playerEggThrowEvent, Player player) {
        playerEggThrowEvent.setHatching(false);

        // Give player their egg back
        player.getInventory().addItem(new ItemStack(Material.EGG, 1));

        player.sendMessage("Sorry too many animals nearby!");
    }
}
