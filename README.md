# DS-Ads Ultimate

DS-Ads is a Minecraft plugin designed to enhance your server's advertisement capabilities. With DS-Ads, you can send customized ads to players via chat, boss bars, action bars, and titles. This plugin is fully configurable and supports permissions for managing who can send and manage ads.

## Features

- Customizable welcome messages for new players.
- Configurable ad formats including chat, boss bar, action bar, and title.
- Permissions to control ad management and sending.
- Easy configuration and reloading of settings.

## Installation

1. Download the latest version of the DS-Ads plugin.
2. Place the plugin jar file in your server's `plugins` directory.
3. Restart your server to generate the default configuration files.
4. Edit the `Settings.yml` file to customize the plugin settings to your needs.
5. Use the `/ads reload` command to apply the changes without restarting the server.

## Configuration

The configuration file (`Settings.yml`) includes settings for plugin behavior, permissions, and customizable messages. Below is an example of the configuration options:

```yaml
# Plugin Settings, you can edit them according to your needs.
Plugin-Configuration:
  Enabled: true
  Disable-MCJoinLeave-Messages: true

# Plugin permissions
Plugin-Permissions:
  Send-Ads: ads.send
  Manage-Ads: ads.manage
  Reload-Plugin: ads.reload

# Plugin messages, you can edit them according to your needs.
Messages:
  Prefix: "<yellow>DS-<red>ADS <dark_gray><bold>» <reset>"
  No-Perms: "<red>You dont have permissions to run this command"
  No-Subcmd: "<red>This command doesnt exist"
  No-Ad-Found: "<red>This ad doesnt exist"
  No-Player-Found: "<red>This player doesnt exist"
  Ad-Failed: "<red>Error sending ad to player"
  Reloaded-Sucess: "<green>Config was reloaded correctly"
  Only-players: "<red>This command can only be used by players"
  Disabled-Plugin: "<red>The plugin was successfully disabled"
  Already-Disabled-Plugin: "<red>The plugin is already disabled"
  Enabled-Plugin: "<green>The plugin was successfully enabled"
  Already-Enabled-Plugin: "<red>The plugin is already enabled"
  Everyone-Send: "<green>Ad sent to all players"
  Player-Send: "<green>Ad sent correctly to the player"
  Usage: "<yellow>Usage: /ads send <AdName> <Player/*>"

# Advertisement configuration
Ads-Configuration:
  Ads-Cooldown: 15 # In Seconds, For all ads
  Ad-Example-1:
    worlds:
      - world
    permission: ads.ad1
    Chat:
      Enabled: true
      Sound: BLOCK_NOTE_BLOCK_PLING
      Ad-content:
        - ''
        - '<dark_gray><st>-----------------------------------------------------'
        - ''
        - '<center><aqua><bold>Hi %player_name%</bold></aqua></center>'
        - ''
        - '<center><gray>What do you think of this plugin?</gray></center>'
        - ''
        - '<dark_gray><st>-----------------------------------------------------'
    Bossbar:
      Enabled: true
      Message: "<gradient:#ff5555:#5555ff>Look, a Bossbar with MiniMessage</gradient>"
      Color: PINK
      Style: SOLID
    Actionbar:
      Enabled: true
      Message: "<gradient:#55ff55:#5555ff>And here you have one in ActionBar</gradient>"
    Title:
      Enabled: true
      Title: "<blue>DS-Ads</blue>"
      Subtitle: "<yellow>Thanks for another opportunity!</yellow>"
      FadeIn: 10
      Stay: 70
      FadeOut: 20
```

## Permissions

- `ads.send` - Allows the user to send ads.
- `ads.manage` - Allows the user to manage ads.
- `ads.reload` - Allows the user to reload the plugin configuration.

## Commands

- `/ads send <AdName> <Player/*>` - Sends the specified ad to a player or all players.
- `/ads reload` - Reloads the plugin configuration.
- `/ads enable` - Enables the plugin.
- `/ads disable` - Disables the plugin.

## Contribution

We welcome contributions from the community! If you have ideas for new features, find a bug, or want to improve the code, feel free to open an issue or submit a pull request. Please ensure your contributions adhere to our coding standards and include appropriate tests where applicable.

## Community Help

If you need help with the plugin, you can:

1. Check the [Wiki](https://wiki.dragonstudio.site) for detailed documentation.
2. Join our [Discord server](https://discord.dragonstudio.site) to ask questions and get support from the community.
3. Open an issue on GitHub if you find any bugs or have feature requests.

## Code of Conduct

We expect all contributors to adhere to our [Code of Conduct](CODE_OF_CONDUCT.md). Please read it to understand the expected behavior in our community.

## License

DS-Ads is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for more details.

## No Plagiarism

We take plagiarism seriously. Ensure that your contributions are original and appropriately attributed if they build upon the work of others. Plagiarized contributions will not be accepted and may lead to being banned from the project.

---

Thank you for using DS-Ads! We hope it enhances your Minecraft server experience.
