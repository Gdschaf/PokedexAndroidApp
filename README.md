# PokedexAndroidApp

## Summary:
This Android app was created as a week-long challenge, allowing the use of any public API. For this project, the PokeAPI was chosen due to its beta GraphQL endpoint. The app was developed using modern Android development technologies, including Kotlin, Kotlin coroutines, Jetpack Compose, and others. The main goal was to treat the challenge as if it were a professional project, showcasing best practices and attention to detail.

## Screens/UI:
The app features two main screens: the Pokedex screen, serving as the homepage, and the Pokemon detail screen. Both screens are fully built with Compose, and reusability was kept in mind when designing composables. Additionally, both light and dark themes are supported throughout the app.

![pokedex_app_demo_light](https://github.com/Gdschaf/PokedexAndroidApp/assets/76528786/c2cd553d-5e24-4360-91a1-726c9bddc35a)

## Accessibility (A11y):
Ensuring accessibility is a crucial aspect of app development, and this app was designed with inclusivity in mind. Both screens, the Pokedex screen, and the Pokemon detail screen, feature full TalkBack support. Elements are appropriately ordered and grouped together with the right content descriptions to facilitate a smooth user experience for those relying on assistive technologies.

Regarding color palettes, I attempted to strike a balance between aesthetic appeal and accessibility. While I didn't check the contrast ratio for every color combination, I took inspiration from a retro color scheme. However, it's important to acknowledge that some color combinations may not meet the minimum contrast ratio of 4.5:1 for optimal accessibility.

One challenge encountered during development was accommodating larger font sizes. Although the UI design aims to be accommodating, it may not be fully optimized for larger fonts. To address this, most text fields were set to single-line with an overflow type that adds ellipsis, reducing the risk of UI layout issues. While efforts were made to make the UI more robust for larger fonts, there is room for improvement in future updates.

## Notable Features:
Several notable features were added to the app, enhancing the overall user experience:

**Dominant Color:** To address the issue of determining background colors for images, the dominant color of each Pokemon's larger image was extracted using the palette-ktx module. This dominant color is used as the background, making the UI more visually appealing.

**Custom Back Button:** Instead of using the default toolbar with a back button, a custom IconButton composable was created. The back button icon was designed using Illustrator, and its transparency allows the dominant color from the background to show through.

**Stat Bar Graph:** To display each Pokemon's base stats more visually, a bar graph was created. This graph provides an easy-to-read visual representation of the stats, making it simpler for users to comprehend the data.

## Testing:
Extensive testing was performed during the development process, focusing on unit tests. JUnit, Mockito, and Apollo's mock server were used to create test cases. A document with detailed test cases was prepared for a potential bug bash to ensure the app's robustness. The document can be accessed here:
[Bug Bash Test Cases](https://docs.google.com/spreadsheets/d/17q23UqSX26u6LsCewFgVi_28bHhbKhRfNkTDqcv4bmY/edit?usp=sharing)

## Known Issues:
The app exhibits a few known issues, mostly related to UI elements:

**Pokedex Number Display:** Pokedex numbers above 999 may get cut off due to the UI design. This issue primarily affects a small number of Pokemon.

**Missing Large Images:** Some Pokemon, particularly from the latest generation, do not have large images available in the data domain used by the app. As a result, a blank view fills the space where the image would be displayed.

**GraphQL Endpoint Reliability:** As the GraphQL endpoint is still in beta, occasional issues may arise when it goes down. Users experiencing loading delays or retry prompts should check the PokeAPI Status page for current issues:
[PokeAPI Server Status](https://pokeapi.statuspage.io/#)

## Critiques:
As with any project, there are areas for improvement:

Code Architecture and System Design: While solid patterns were used, there might be room for exploring more efficient approaches to solving certain problems.

Unit Test Granularity: The unit tests focused heavily on Apollo queries and specific queries. Breaking up these tests into smaller, more specific ones could enhance traceability in case of failures.

Other Considerations:
Several considerations were made during development, leading to certain decisions:

Dagger: While dependency injection is appealing, for this project's scope, it was deemed unnecessary. Injecting the Apollo client and repositories into view models would have added complexity without significant benefits.

Multiple Modules: While multiple modules have been used for larger projects, it was not needed for this app's scale, as data models and repositories were kept together.

RecyclerView vs. LazyColumn: The choice between RecyclerView and LazyColumn for the Pokedex was initially challenging. Ultimately, Jetpack Compose's LazyColumn was chosen, as it performed well on physical devices.

