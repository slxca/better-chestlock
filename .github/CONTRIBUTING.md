# Contributing to Better Chestlock

Thanks for taking the time to contribute!

## Getting started

Requirements: JDK 25.

```bash
git clone https://github.com/slxca/better-chestlock.git
cd better-chestlock
```

## Building

```bash
# Generate data (loot tables, recipes, translations, advancements)
./gradlew runDatagen

# Compile + build the jar
./gradlew build
```

The built jar is at `build/libs/better-chestlock-<version>.jar`.

## Project structure

```
src/main/java/com/slxca/betterChestlock/
├── BetterChestlock.java          # Mod initializer (wires everything)
├── ModCreativeTabs.java          # Creative tab entries
├── block/                        # Locked Chest block + registries
├── block/entity/                 # Locked Chest block entity + registries
├── command/                      # /chest commands + chest info display
├── config/                       # Config file loading
├── item/                         # Lock item registry
└── protection/                   # Access rules + trust data storage

src/client/java/com/slxca/betterChestlock/client/
├── block/entity/                 # Chest renderer
├── datagen/                      # Loot, recipes, translations (generated)
└── mixin/                        # Client mixins
```

Generated data (loot tables, recipes, translations) lives under
`src/main/generated` and is produced by `./gradlew runDatagen`.

## Code style

- Follow the existing package structure and naming conventions.
- Keep the code minimal and readable; extract helpers into their own classes
  when a file grows.
- Do **not** add comments unless they clarify non-obvious logic.

## Translations

User-facing messages are translatable. When you add or change a message:

1. Update the key in the code (e.g. `message.better-chestlock.<name>`).
2. Add the entry to `EnglishLanguageProvider` and `GermanLanguageProvider`
   under `src/client/.../client/datagen/`.
3. Run `./gradlew runDatagen` to regenerate the lang files.

## Submitting changes

1. Create a branch with a descriptive name.
2. Make your changes and verify `./gradlew build` (and `runDatagen` if data changed).
3. Open a pull request using the provided template.

By contributing, you agree that your contributions are licensed under the
[MIT License](LICENSE.txt).