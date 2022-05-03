#!/bin/bash

while getopts a: flag
do
    case "${flag}" in
        a) application=${OPTARG};;
    esac
done

if [ -z "$application" ]
  then
    echo "No application argument supplied"
    exit 1
fi

application_lower=${application,,}
application_upper=${application^^}
application_camel=${application^}

echo "Setting up repository for $application_lower-sdk-extensions-java";
echo "Creating directories ..."
mkdir -p sdk/src/main/java/com/finbourne/$application_lower/extensions/auth

echo "Setting up pom.xml ..."
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" pom.xml
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" pom.xml
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" pom.xml

echo "Setting up GitHub actions ..."
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" .github/workflows/build-and-test.yaml
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" .github/workflows/build-and-test.yaml
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" .github/workflows/build-and-test.yaml
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" .github/workflows/cron.yaml
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" .github/workflows/cron.yaml
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" .github/workflows/cron.yaml

echo "Setting up README ..."
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" README.md
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" README.md
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" README.md

echo "Setting up CONTRIBUTING ..."
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" docs/CONTRIBUTING.md
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" docs/CONTRIBUTING.md
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" docs/CONTRIBUTING.md

echo "Setting up Docker ..."
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" Dockerfile
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" Dockerfile
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" Dockerfile
sed -i '' "s/CHANGEME-APPLICATION-LOWER/$application_lower/g" docker-compose.yml
sed -i '' "s/CHANGEME-APPLICATION-UPPER/$application_upper/g" docker-compose.yml
sed -i '' "s/CHANGEME-APPLICATION-CAMEL/$application_camel/g" docker-compose.yml

echo "Moving files ..."
mv pom.xml sdk/
mv Dockerfile sdk/
mv docker-compose.yml sdk/
