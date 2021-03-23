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
         emailext body: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS: Check console output at $BUILD_URL to view the results.', 
         recipientProviders: [developers(), requestor()], subject: 'test subject', to: 'test@jenkins'
      }
    }
}
  

}

