# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This is an Obsidian knowledge base containing technical documentation, project notes, and personal information across multiple work and personal projects. The repository is version-controlled with git and synced to GitHub at `https://github.com/stephenlorenz/knowledge_base.git`.

## Repository Structure

The vault is organized into top-level project directories, each containing related markdown notes:

- **OCTO/**: Primary active work project documentation
  - Contains daily notes in `OCTO/Daily Notes/` following the template in `OCTO/_templates/`
  - Project management notes organized by feature (Posting Tool, Search Page, User Profile, Migration, Contract Admin Tools)
  - API documentation in `OCTO/API Documentation/`

- **Rally/**: ResearchAlly platform documentation
  - Developer documentation in `Rally/Developer Tools/`
  - Database migration notes in `Rally/Database/`
  - Version-specific deployment notes in subdirectories like `Rally/v2.1.0/`, `Rally/v2.2.0.0 AWS Infrastructure Upgrade/`

- **DSC2U/**, **DSP/**, **Narath/**: Other work project documentation

- **Docker/**, **PostgreSQL/**, **MacOS Tips/**, **Testing/**: Technical reference notes

- **House/**, **Email Drafts/**, **Scrapbook/**, **General To Do/**: Personal notes and drafts

### Nested Vaults

This repository contains multiple nested Obsidian vaults (directories with `.obsidian/` subdirectories). Each major project area may have its own Obsidian configuration. The main vaults include OCTO, Rally, and several others.

## Working with This Repository

### Obsidian-Specific Conventions

- **Wikilinks**: Internal links use Obsidian's `[[Page Name]]` syntax for cross-references
- **Frontmatter**: Many notes include YAML frontmatter with tags and metadata
- **Templates**: The OCTO vault uses templates in `_templates/` for daily notes and task tracking
- **Tags**: Notes use tags like `#dailynote`, `#todo` for organization

### File Operations

- **Creating new notes**: Place notes in the appropriate project directory
- **Links**: When creating new notes, use Obsidian wikilink syntax `[[Note Name]]` for cross-references
- **Images**: Store images in project-specific `_images/` directories or inline with notes
- **Templates**: When creating structured notes (e.g., daily notes, tasks), refer to existing templates in `_templates/` directories

### Git Operations

- This is a personal knowledge base; commits should preserve the user's work and organizational structure
- Be cautious with bulk operations that might affect multiple project areas
- The repository tracks work across multiple active and historical projects

### Search and Navigation

- Use file paths to search within specific project areas (e.g., `OCTO/**/*.md` for OCTO notes)
- Daily notes follow the format `YYYY-MM-DD.md` in project-specific `Daily Notes/` directories
- API and technical documentation is typically in dedicated subdirectories within each project

## Important Notes

- This repository contains both work and personal information
- Multiple project contexts are interleaved; pay attention to which project area you're working in
- Some notes reference external systems (GitHub, YouTrack, AWS, databases)
- Development notes often include code snippets for Java, JavaScript, SQL, and shell commands
