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
  def currentCommit = $(git show --name-only)
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
         env Current commit: ${env.currentCommit} <br/>
         Current commit: ${currentCommit} <br/>
         
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

