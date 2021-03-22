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
  
  pipeline {
    agent any
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def num1 = config.NUM1
    def num2   = config.NUM2
    def name = config.NAME
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
  

}

