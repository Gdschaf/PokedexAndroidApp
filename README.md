# PokedexAndroidApp

## Summary:
This Android app was created as a week-long challenge, allowing the use of any public API. For this project, the PokeAPI was chosen due to its GraphQL endpoint. The app was developed using modern Android development technologies, including Kotlin, Kotlin coroutines, Jetpack Compose, and others. The main goal was to treat the challenge as if it were a professional project, showcasing best practices and attention to detail.
<br><br>
![pokedex_app_demo_light](https://github.com/Gdschaf/PokedexAndroidApp/assets/76528786/c2cd553d-5e24-4360-91a1-726c9bddc35a)

## Screens/UI:
The app features two main screens: the Pokedex screen, serving as the homepage, and the Pokemon detail screen. Both screens are fully built with Compose, and reusability was kept in mind when designing composables. Additionally, both light and dark themes are supported throughout the app. The color pallet was inspired by a retro theme and I hope it meets the 4.5:1 minimum contrast ratio for optimal accessibility.

I used palette to get the dominant color of the Pokemon and its main type. You can see those being used as the Pokemon’s background on its detail page as well as its Pokedex number’s background. This is a relatively small detail but I really enjoy how this looks and I think it adds an extra level of polish to the UI.

I created two Composables that act as full screens for when a query is loading and if there are any errors. This way the user isn’t sitting at a blank screen while waiting for the data to load. If something goes wrong and we get an error back, we display a “something went wrong” screen with a retry button that tries to fetch the data again. These screens were made with re-usability in mind so any other screens that need this, can use it and it’d remain aesthetically consistent throughout the app.

I also created a custom bar graph type composable that’s used to represent the base stats of a pokemon. This is another small detail I’m happy with how it turned out. This graph provides an easy-to-read visual representation of the stats, making it simpler for users to comprehend the data rather than just looking at a bunch of text fields.

## Accessibility (A11y):
Both screens, the Pokedex screen, and the Pokemon detail screen, feature full TalkBack support. Elements are appropriately ordered and grouped together with the right content descriptions to facilitate a smooth user experience for those relying on assistive technologies.

Larger font sizes can look a bit rough, however. I tried designing the UI in a way that’d accommodate larger font sizes. I set all text fields to only have 1 line and their overflow type to append an ellipsis to the end. This way as the font gets bigger, it doesn’t necessarily break the UI, however some areas could be more legible.

## Testing:
I did a lot of testing during development. I created a handful of unit tests using JUnit, Mockito, and Apollo’s mock server you can check out in the project. <br>
Testing the view models proved to be a little difficult because of the coroutines. Everything I read online via forums and api documentation said to have a rule that overrides the setMain on the Dispatchers which I did, but that seemed to only work half of the time. Sometimes the tests would pass, other times it'd fail. Seemed random and it was always complaining about the context of the coroutine in the test. <br>
I also created a spreadsheet with test cases for a potential bug bash. That document can be viewed here:
[Bug Bash Test Cases](https://docs.google.com/spreadsheets/d/17q23UqSX26u6LsCewFgVi_28bHhbKhRfNkTDqcv4bmY/edit?usp=sharing)

## Known Issues:
There are a few known issues that are mostly UI related.

**Pokedex Number Display:** Pokedex numbers above 999 may get cut off due to the UI design. This issue only affects like 10 Pokemon.

**Missing Large Images:** The Pokemon in the latest generation don’t have large images and therefore nothing will load. I could have put something in place of the image but right now it’s just a blank view to at the very least not mess up the UI formatting.

**GraphQL Endpoint Reliability:** The GraphQL endpoint is still in beta so occasionally issues may arise when it goes down. It claims to have a 98% uptime but I had a day where it was down for almost 5 hours. If you’re experiencing loading delays or retry prompts, you should check the PokeAPI Status page for current issues:
[PokeAPI Server Status](https://pokeapi.statuspage.io/#)

## Critiques:
There's always room for improvement and I'm always looking for new approaches to problems. 

**Code Architecture:** I think I could have organized some of the files and functions a bit better, namely the shared stuff. That ended up being kind of a catch all for things that didn't have a home.

**System Design:** While I think I went with patterns that suited the given problems well, I always think there's better, more clever way out there to solve the same problems.

**Unit Test Granularity:** The unit tests focused heavily on the repositories. These repositories do test a lot of things when it come to data ingestion and I think it covers a lot of cases. However if I were to break these tests up into smaller, more focused tests, it'd be easier to trace back any issues.

## Other Considerations:
Several considerations were made during development, leading to certain decisions:

**Multiple Modules:** Kind of going back to architecture improvements above, I contemplated breaking things out into their own modules but again, due to the scope of the project, decided against it.

**RecyclerView vs. LazyColumn:** As much as I wanted to use Compose for everything, I did toss around the idea of using a RecyclerView as I've heard they're more efficient with larger data sets. I didn't see any noticeable performance loss from using a LazyColumn though so ultimately went that route.

## Feedback

I received some great feedback which helped me make improvements. <br>
I added dagger hilt to the project. There wasn't a ton to inject but it cleans up somethings and really comes in handy. <br>
I touched on the view model tests cases a bit above but added test cases for both view models. They are fairly similar since they kind of do the same stuff. <br>
I reworked that state data in the view models and included data classes for each view model's data. This way the composable only has to look at one variable for updating. <br>
I moved the fetchData outside of the init in both view models for testing. To prevent that from getting called a million times I added a loading status enum to both view models so it knows when it's loading, has an error, or has/hasn't been initialized. All of which is tested in the view model unit tests. <br>
I also did many general passes to improve and clean up code. I also added ktlint to make sure everything was formatted correctly. <br>
There's a bunch more little things I did to improve readability, scalability, and reliability and I'm sure I could keep improving it further, but I'm really happy with the feedback provided and the updates made.


## Summary

I am super happy with how this project turned out. It may not be perfect, but I hope it comes across how much fun I had with this project. I tried to not only meet but exceed expectations of what a two screen/activity app can look like with only a weeks worth of work. As always, I'd love to hear any and all feedback. I have a laundry list of stretch goals/features I didn't get around to that I think would be fun to add. Thanks for taking the time to look at my project!

## Project Details
**Gradle Version:** 8.1.0<br>
**Gradle JDK:** JetBrains Runtime version 17.0.6 (jbr-17)<br>
**Minimum SDK:** 23<br>
**Target SDK:** 33<br>

## Attributions
I just want to add a few attributes here for the lovely art I found online and used in the app <br>
The pokemon type icons were found [Here](https://www.deviantart.com/lugia-sea/art/Pokemon-Type-Icons-Vector-869706864). <br>
and the app icon was found [Here](https://365webresources.com/pokemon-ios-app-icons/). <br>