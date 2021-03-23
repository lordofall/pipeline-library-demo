#!/usr/bin/env groovy

def call(body) {
  
  /*def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  def num1 = config.NUM1
  def num2   = config.NUM2
  def name = config.NAME
  echo "sum "+num1+num2
  echo "Hello, ${name}." */
  
  /*node {
    def mvnHome
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def num1 = config.NUM1
    def num2   = config.NUM2
    def name = config.NAME
    stage('Preparation') { // for display purposes
        echo "sum "+num1+num2
        echo "Hello, ${name}."
    }
  }*/
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def num1 = config.NUM1
  def num2   = config.NUM2
  def name = config.NAME
  pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo "sum "+num1+num2
                echo "Hello, ${name}."
            }
        }
    }

    post {
      always {
         emailext body: """
         ${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${currentBuild.currentResult}<br/>
         Check console output at ${env.BUILD_URL} to view the results.<br/>
         Job name: ${env.JOB_NAME} <br/>
         BRANCH_NAME: ${env.BRANCH_NAME} <br/>
         CHANGE_ID: ${env.CHANGE_ID} <br/>
         CHANGE_URL: ${env.CHANGE_URL} <br/>
         CHANGE_TITLE: ${env.CHANGE_TITLE} <br/>
         CHANGE_AUTHOR: ${env.CHANGE_AUTHOR} <br/>
         CHANGE_AUTHOR_DISPLAY_NAME: ${env.CHANGE_AUTHOR_DISPLAY_NAME} <br/>
         CHANGE_TARGET: ${env.CHANGE_TARGET} <br/>
         CHANGE_BRANCH: ${env.CHANGE_BRANCH} <br/>
         BUILD_NUMBER: ${env.BUILD_NUMBER} <br/>
         JOB_NAME: ${env.JOB_NAME} <br/>
         BUILD_URL: ${env.BUILD_URL} <br/>
         JOB_URL: ${env.JOB_URL} <br/>
         current build causes: ${currentBuild.buildCauses}<br/>
          """, 
        subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
        recipientProviders: [developers(), requestor()], to: 'test@jenkins'
      }
    }
}
  

}

