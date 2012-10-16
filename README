===== Create a release =====

You must have a local master branch. If needed : git checkout -b master origin/master

export VERSION=0.3.1
git checkout -b release-$VERSION develop
mvn release:prepare
mvn release:perform
git checkout develop
git merge --no-ff release-$VERSION
git branch -d release-$VERSION
git push origin develop
#we need to suppress remote branch created by mvn release:prepare
git push origin :release-$VERSION 
git checkout master
git merge --no-ff V2-$VERSION
git push origin master

