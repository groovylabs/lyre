# Contributing to Lyre

Lyre is maintained by Groovylabs which is a group of developers focused on learn, experiment, share, create, contribute to projects and initiatives.
We're **actively** looking for more contributors right now. If you're interested in becoming a Lyre maintainer or in any other project from Groovylabs, please contact us.

<a name="bugs"></a>
## 🐛 [Bug reports](https://github.com/groovylabs/lyre/wiki/Report-a-Bug)

## Get Involved

* Maintaining Lyre.
  * Resolve, fix, triage and close [issues.](https://github.com/groovylabs/lyre/issues)

## Using the issue tracker

The issue tracker is the preferred channel for [bug reports](#bugs),
[features requests](#features) and [submitting pull
requests](#pull-requests), but please respect the following restrictions:

* Please **do not** derail or troll issues. Keep the discussion on topic and
  respect the opinions of others.

<a name="features"></a>
## Feature requests

Feature requests are welcome. But take a moment to find out whether your idea
fits with the scope and aims of the project. It's up to *you* to make a strong
case to convince the project's developers of the merits of this feature. Please
provide as much detail and context as possible.

<a name="pull-requests"></a>
## Pull requests

Good pull requests - patches, improvements, new features - are a fantastic
help. They should remain focused in scope and avoid containing unrelated
commits.

**Please ask first** before embarking on any significant pull request (e.g.
implementing features, refactoring code), otherwise you risk spending a lot of
time working on something that the project's developers might not want to merge
into the project.

Please adhere to the coding conventions used throughout a project (indentation,
accurate comments, etc.) and any other requirements (such as test coverage).

Adhering to the following this process is the best way to get your work
included in the project:

1. [Fork](http://help.github.com/fork-a-repo/) the project, clone your fork,
   and configure the remotes:

   ```bash
   # Clone your fork of the repo into the current directory
   git clone https://github.com/<your-username>/lyre
   # Navigate to the newly cloned directory
   cd lyre
   # Assign the original repo to a remote called "upstream"
   git remote add upstream https://github.com/groovylabs/lyre
   ```

2. If you cloned a while ago, get the latest changes from upstream:

   ```bash
   git checkout master
   git pull upstream master
   ```

3. Create a new topic branch (off the main project development branch) to
   contain your feature, change, or fix:

   ```bash
   git checkout -b <topic-branch-name>
   ```

4. Make sure to update, or add to the tests when appropriate. Patches and
   features will not be accepted without tests. Run `./mvnw clean test` to check that
   all tests pass after you've made changes.

5. Commit your changes in logical chunks. Use Git's
   [interactive rebase](https://help.github.com/articles/interactive-rebase)
   feature to tidy up your commits before making them public.

6. Locally merge (or rebase) the upstream development branch into your topic branch:

   ```bash
   git pull [--rebase] upstream master
   ```

7. Push your topic branch up to your fork:

   ```bash
   git push origin <topic-branch-name>
   ```

8. [Open a Pull Request](https://help.github.com/articles/using-pull-requests/)
    with a clear title and description.

9. If you are asked to amend your changes before they can be merged in, please
   use `git commit --amend` (or rebasing for multi-commit Pull Requests) and
   force push to your remote feature branch. You may also be asked to squash
   commits.

10. If you are asked to squash your commits, then please use `git rebase -i master`. It will ask you to pick your commits - pick the major commits and squash the rest.

**IMPORTANT**: By submitting a patch, you agree to license your work under the
same license as that used by the project.


<a name="maintainers"></a>
## Maintainers

If you have commit access, please follow this process for merging patches and cutting new releases.

### Reviewing changes

1. Check that a change is within the scope and philosophy of the project.
2. Check that a change has any necessary tests and a proper, descriptive commit message.
3. Checkout the change and test it locally.
4. Apply the change to `master` locally (feel free to amend any minor problems in the author's
   original commit if necessary).
5. If the change is good, and authored by another maintainer/collaborator, give
   them a "Groovy code!" comment and let them handle the merge.

### Submitting changes

1. All non-trivial changes should be put up for review using GitHub Pull
   Requests.
2. Your change should not be merged into `master` (or another feature branch),
   without at least one "Groovy code!" comment from another maintainer/collaborator
   on the project. "Looks good to me" is not the same as "Groovy code!".
3. Merge changes using GitHub's merge button or locally rebase your change onto
   `master` and then push to GitHub.
4. Once a feature branch has been merged into its target branch, please delete
   the feature branch from the remote repository.

### Releasing a new version

1. Include all new functional changes in the CHANGELOG.
2. Use a dedicated commit to increment the version. The version needs to be
   added to the `CHANGELOG.md` (inc. date).
3. The commit message must be of `0.0.0` format.
4. Create an annotated tag for the version: `git tag -m "0.0.0" 0.0.0`.
5. Push the changes and tags to GitHub: `git push --tags origin master`.
6. Publish the new version to GitHub and DockerHub.
