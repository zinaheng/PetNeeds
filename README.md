# PetNeeds
Original App Design Project - README Template
===

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
[PetNeeds aim to help pet owners to locate places around them that is needed for their pet]

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Lifestyle
- **Mobile:** This application is most suited for mobile as it utilizes features such as maps and location which are most useful when the user is on the go.
- **Story:** This application helps pet owners find businesses and services aimed at taking care of their pet needs such as vets, groomers, trainers and even dog parks.
- **Market:** Any pet owner could use this app to effectively find pet businesses and services in their area.
- **Habit:** This app could be used once a week or more depending on the needs of the user.
- **Scope:** First we will focus on finding the right businesses based on location and category chosen by the user. Then we could expand into creating a community chat where users can interact with other pet owners in their area and ask questions, make recommendations etc. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Splash screen displays logo and name.
* User can use their current location to find businesses near them.
* User can enter the trype of the business looking for in the search bar.
* User can scroll through suggested businesses in a list.
* User is sent to a detail screen where the business they select will be shown with more information


**Optional Nice-to-have Stories**

* User can login.
* User can register a new account.
* User can access their own profile.
* User can communicate with other users in a community chat.

### 2. Screen Archetypes

* [Splash screen]
   * [x] display the app logo 
   * [x] display the app name
* [Stream screen]
   * [x] User can enter their zipcode in a search bar to find businesses near them.
   * [x] User can choose business categories from a dropdown menu.
   * [x]user can scroll through suggested businesses in a list.
* [Maps]
   * [x] Business that fit the category and are close to the user location are diplayed on the map.
* [Detail screen]
   * [x] address
   * [x] hours of operation
   * [x] rating

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [Logo screen]
* [Main screen]
* [Search Place]
* [detailed options]

**Flow Navigation** (Screen to Screen)

* [Logo screen]
   * [Login/Register] OPTIONAL
   * [Stream screen]
   
* [Stream screen]
   * [Detail screen]
   * [Profile screen] OPTIONAL
   * [Community chat] OPTIONAL

## Wireframes

<img src="https://github.com/zinaheng/PetNeeds/blob/master/Pet%20Needs.jpg" width=2200>

https://www.figma.com/proto/hAGpu1mKyLV8ievdzIzLrF/Pet-Needs?node-id=9%3A2&scaling=min-zoom

## Schema 
[This section will be completed in Unit 9]
### Models
Model: Post
 | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the object that we retrieve |
   | image         | File     | data fetched from the google maps |
   | rating     | Number | number of ratings for business |
   | business name | String  | name of the business |
   | address    | String  | location fetched from API|
   | phone number     | String | phone number of the business |
   | time     | DateTime | business hours |
   
  Model: Get
    | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | search     | Number   | search area based on zip code |
   
 ## Network
  List fo network requests by each screen
  * Main Feed
      * (Read/GET) query all the businesses by Zipcode 
      * ![Screen Shot 2021-04-09 at 6 33 12 PM](https://user-images.githubusercontent.com/44506873/114250398-32525280-9963-11eb-8980-d0b2a6b7f8a1.png)

  * Details Feed
      * (Read/Get) query business detail information!
- [OPTIONAL: List endpoints if using existing API such as Yelp]

## User Stories - Sprint 3

The following **required** functionality is completed:

- [x] Implemented the navigation from MainActivity to DetailActivity
- [x] Implemented the list view in DetailActivity
- [x] Updated the map pin
## Video Walkthrough

Here's a walkthrough of implemented user stories in sprint 2:

<img src= 'petneeds_walkthrough.gif' width=''><br>
