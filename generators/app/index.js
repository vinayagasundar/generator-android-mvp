'use strict';

var Generator = require('yeoman-generator');
const mkdirp = require('mkdirp');
const yosay = require('yosay');
const chalk = require('chalk');


module.exports = class extends Generator {
  initializing() {
    this.props = {};
  }

  prompting() {
    this.log(yosay(
      'Welcome to the rad ' + chalk.red('Android MVP') + ' generator!'
    ));

    const prompts = [
      // {
      //   name: 'name',
      //   message: 'What are you calling your app?',
      //   store: true,
      //   default: this.appname // Default to current folder name
      // },
      {
        name: 'package',
        message: 'What package will you be publishing the app under?',
        store: true
      },
      {
        name: 'targetSdk',
        message: 'What Android SDK will you be targeting?',
        store: true,
        default: 25 // Android 7.1.1 (Nougat)
      },
      {
        name: 'minSdk',
        message: 'What is the minimum Android SDK you wish to support?',
        store: true,
        default: 21 // Android 5.0 (Lollipop)
      }];

    return this.prompt(prompts).then((answers) => {
      this.props.appPackage = answers.package;
      this.appName = answers.name;
      this.appPackage = answers.package;
      this.props.androidTargetSdkVersion = answers.targetSdk;
      this.props.androidMinSdkVersion = answers.minSdk;
    });
  }

  writing() {
    var packageDir = this.props.appPackage.replace(/\./g, '/');

    mkdirp('app');
    mkdirp('app/src/main/assets');
    mkdirp('app/src/main/java');
    mkdirp('app/src/androidTest/java');
    mkdirp('app/src/test/java');

    var appFolder = '.';

    var appPath = this.sourceRoot() + '/';

    this.fs.copy(appPath + 'gitignore', '.gitignore');
    this.fs.copy(appPath + 'gradle.properties', 'gradle.properties');
    this.fs.copy(appPath + 'gradlew', 'gradlew');
    this.fs.copy(appPath + 'gradlew.bat', 'gradlew.bat');
    this.fs.copy(appPath + 'dependencies.gradle', 'app/dependencies.gradle');
    this.fs.copy(appPath + 'settings.gradle', 'settings.gradle');
    this.fs.copy(appPath + 'app/gitignore', 'app/.gitignore');
    this.fs.copy(appPath + 'app/proguard-rules.pro', 'app/proguard-rules.pro');
    this.fs.copy(appPath + 'app/build.gradle', 'app/build.gradle');

    this.fs.copy(appPath + 'gradle', 'gradle');
    this.fs.copy(appPath + 'app/src/main/res', 'app/src/main/res');
    // this.fs.copy(appPath + 'app/src/main/assets', 'app/src/main/assets');

    this.fs.copyTpl(appPath + 'build.gradle', 'build.gradle', this.props);

    this.fs.copyTpl(appPath + 'app/src/androidTest/java/io', 'app/src/androidTest/java/' + packageDir + '/', this.props);
    this.fs.copyTpl(appPath + 'app/src/main/java/io', 'app/src/main/java/' + packageDir + '/', this.props);
    this.fs.copyTpl(appPath + 'app/src/test/java/io', 'app/src/test/java/' + packageDir + '/', this.props);


  }
};