# Angular Quick Start

## What is Angular?

1. Angular is a web framework which is most commonly used on the client (e.g. web browser) to create dynamic web pages.
2. There are two main flavors of Angular:
	1. AngularJS
		1. Uses JavaScript as the core language
		2. Core design problems, hightlighted by the competition (e.g. React) caused Google to scrap AngularJS
	2. Angular
		1. Uses Typescript as the core language (which is later transpiled into JavaScript)
		2. Use modular design with 
		3. Would be a pain to code completely by hand, so Angular CLI (command line interface) comes to the rescue
		4. Needs to be compiled ahead of time like many rebust programming languages
			1. This compilation greatly speeds up the application at runtime
3. Rally has been around long enough that we use both flavors, however, all new development is being done in Angular

## Quick Start

_Note: You will need to use the terminal or command-line on your computer to perform the steps described in this Quick Start_

1. Download and install NodeJs and NPM:
	- https://docs.npmjs.com/downloading-and-installing-node-js-and-npm
	- https://nodejs.org/download/release/latest-v12.x/
2. Using your newly installed npm application, install Angular CLI (https://angular.io/cli):
	```
	npm install -g @angular/cli
	```
3. Create a new directory on your computer and navigate to this directory from the terminal
4. To create a new Angular project type:
	```
	ng new demo-project
	```
5. When prompted:
	```
	Would you like to add Angular routing? [Yes]
	Which stylesheet format would you like to use? [SCSS]
	```
6. Change to the newly created directory:
	```
	cd demo-project
	```
7. Start the Angular server:
	```bash
	ng serve
	```
8. In your web browser view your application by going to http://localhost:4200

![[Screen Shot 2021-07-18 at 21.19.55.png]]