# pipeline-mod-mtm

[![Build Status](https://travis-ci.com/mtmse/pipeline-mod-mtm.svg?branch=master)](https://travis-ci.com/mtmse/pipeline-mod-mtm)

MTM specific modules for the DAISY Pipeline 2

## Release procedure
- View changes since previous release and update version number according to semantic versioning.

  ```sh
  git diff v1.0.0...HEAD
  VERSION=1.1.0
  ```

- Create a release branch.

  ```sh
  git checkout -b release/${VERSION}
  ```
  
- Resolve snapshot dependencies and commit.
- Set the version in pom.xml to `${VERSION}-SNAPSHOT` and commit.
- Make release notes and commit. (View changes since previous release with `git diff v1.1.0...HEAD`
  and look for relevant Github issues on [https://github.com/search](https://github.com/search))
- Perform the release with Maven. Note that a server configuration for "sonatype-nexus-staging" must be 
in settings.xml (${maven.home}/conf/settings.xml or ${user.home}/.m2/settings.xml).

```sh
  mvn clean release:clean release:prepare -DpushChanges=false
  mvn release:perform -DlocalCheckout=true -Darguments=-Dgpg.passphrase=[thephrase]
  ```
  
- Push and make a pull request (for turning an existing issue into a PR use the `-i <issueno>` switch).

  ```sh
  git push origin release/${VERSION}:release/${VERSION}
  hub pull-request -b mtmse:master -h mtmse:release/${VERSION} -m "Release version ${VERSION}"
  ```
  
- Stage the artifact on https://oss.sonatype.org and comment on pull request.

  ```sh
  ghi comment -m staged ${ISSUE_NO}
  ```
  
- Test and stage all projects that depend on this release before continuing.
- Release the artifact on https://oss.sonatype.org  and close pull request.

  ```sh
  ghi comment --close -m released ${ISSUE_NO}
  ```
  
- Push the tag.

  ```sh
  git push origin v${VERSION}
  ```
  
- Add the release notes to http://github.com/mtmse/pipeline-mod-mtm/releases/v${VERSION}.
