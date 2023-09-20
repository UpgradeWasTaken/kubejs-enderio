# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog],
and this project adheres to [Semantic Versioning].

## [0.3.0] - 2023-09-20

### Added
- added `max_item_drops` property to fire crafting recipes to limit loot table drops

### Changed
- changed minimum EnderIO version to 6.0.19-alpha
- changed name of exposed binding from `EIOBonusType` to `EnderIOBonusType`
- changed id of sag mill recipes from `sagmilling` to `sag_milling`
- changed grinding ball function name from `powerUse` to `powerUseMultiplier`
- changed enchanter recipes to use counted ingredient instead of an amount property

## [0.2.0] - 2023-09-13

### Added
- added utility method for fire crafting to add single dimensions
- added ability to remove vanilla smelting recipes from the alloy smelter

### Fixed
- fixed swallowed exception when slicer recipes have not exactly 6 inputs

## [0.1.0] - 2023-09-05

Initial 1.20.1 release!

<!-- Links -->
[keep a changelog]: https://keepachangelog.com/en/1.0.0/
[semantic versioning]: https://semver.org/spec/v2.0.0.html

<!-- Versions -->
[0.3.0]: https://github.com/AlmostReliable/kubejs-enderio/releases/tag/v1.20.1-forge-0.3.0-beta
[0.2.0]: https://github.com/AlmostReliable/kubejs-enderio/releases/tag/v1.20.1-0.2.0-forge-beta
[0.1.0]: https://github.com/AlmostReliable/kubejs-enderio/releases/tag/v1.20.1-0.1.0-forge-beta
