#!/usr/bin/env bash
# Script to update all plugin.xml files within a repository using the nsUri of a given ecore file
# author Mat Hansen <mhansen@eclipsesource.com>
# date   20180215

ECORE=$1
XPATH="string(/*[local-name()='EPackage']/@nsURI)"
GIT_ROOT=$(git rev-parse --show-toplevel)
if [ ! -f $ECORE ]; then
  echo "Error: unable to find ECORE"
  exit 1
fi
if [ -z $GIT_ROOT ]; then
  echo "Error: unable to determine git root level directory. Please execute this script within a git repository."
  exit 1
fi

git show HEAD~1:$ECORE > $ECORE.base 2>> /dev/null || git show HEAD~1:./$ECORE > $ECORE.base 2>> /dev/null
nsuri_old=$(xmllint --XPATH $XPATH $ECORE.base)
nsuri_new=$(xmllint --XPATH $XPATH $ECORE)

echo -e "Updating nsURI \n \"$nsuri_old\"\nto\n \"$nsuri_new\"\nwithin\n $GIT_ROOT ..."

find $GIT_ROOT -name plugin.xml -exec echo sed -i -e "s!uri=\"${nsuri_old}\"!uri=\"${nsuri_new}\"!g" {} \;
