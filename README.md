![LUSID_by_Finbourne](./resources/Finbourne_Logo_Teal.svg)

# LUSID<sup>®</sup> Luminesce Java SDK Extensions

These are the Java SDK Extensions to accompany the [Java SDK (Preview) for the Luminesce application](https://github.com/finbourne/luminesce-sdk-java-preview).

This extensions package provides the user with additional extensions to make it easy to configure and use the API endpoints. 

The Luminesce application is part of the [LUSID by FINBOURNE](https://www.finbourne.com/lusid-technology) platform. To use it you'll need a LUSID account. [Sign up for free at lusid.com](https://www.lusid.com/app/signup).

For more details on other applications in the LUSID platform, see [Understanding all the applications in the LUSID platform](https://support.lusid.com/knowledgebase/article/KA-01787/en-us).

## Installation 

Maven artifacts can be downloaded from the Open Source Software Repository Hosting (OSSRH) by adding the following to your pom.xml

```
<project>
  ...
  <repositories>
    <repository>
      <id>osssrh</id>
      <name>OSSRH</name>
      <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>com.finbourne</groupId>
      <artifactId>luminesce-sdk-extensions</artifactId>
      <version>{INSERT VERSION}</version>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```
