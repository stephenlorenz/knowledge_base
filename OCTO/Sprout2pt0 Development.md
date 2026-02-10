The Sprout2pt0 development environment runs inside of a devcontainer. This should allow developers to develop and build the environment with minimal set up.
### Prerequisites

- Docker installed locally
- IDE which supports Dev Containers (e.g. VSCode and IntelliJ Idea)
	- The latest IntelliJ Idea Ultimate versions have built-in support for Dev Containers
	- With VSCode, install the "Dev Containers" extension. This can be found in the Extensions view (Ctrl+Shift+X or Cmd+Shift+X).
- Ports 8084, 8787, and 4300 should be available on your computer
### Installation

1. Clone the Sprout2pt0 project from GibHub, https://github.com/MGH-LCS/sprout2pt0.git

#### Using Intellij IDEA
1. Use the project explorer, open the .devcontainer subdirectory
2. Open the `devcontainer.json` file
3. In the left-hand gutter, there should a cube icon on line 1
4. Clicking on this icon will display a pop-out menu
5. Chose the `Create Dev Container and Clone Sources...` option
6. In the popup window that appears, chose the appropriate Git branch, the default should be fine
7. Click on the `BUILD CONTAINER AND CONTINUE` button
8. This will generate the Dev Container. It can take a few minutes
9. Once the Dev Container is .

#### Using Visual Studio Code (vscode)
1. Open a terminal at the root of the project
2. Navigate to the .devcontainer subdirectory, e.g. `cd .devcontainer`
3. On Linux or Mac, run the deploy.sh script: e.g. `./deploy.sh`
	- This will in generate the Dev Containers
 4. Once the script is complete, open Visual Studio Code
 5. Open the command palette (comand-shift-P on Mac) and run the command `Dev Containers: Attach to Running Container...`'
 6. Chose the sprout2pt0-dev container
 7. From the file explorer, click on the "Open Folder" button
 8. Enter the path `/workspace/`
 9. Open the VS Code terminal
 10. Run the `./start-server.sh ` script
 11. Open `http://localhost:8084`
### Login

- username/email: admin@demo
- password: anything
### Angular Development server (for frontend development)

1. Inside Dev Container command-line, navigate to `/workspace/web/sprout-manager-dashboard/src/main/web`
2. Enter `npm run start`
3. After the Angular Live Development Server starts, open your browser to `http://localhost:4300`
