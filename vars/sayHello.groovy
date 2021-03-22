#!/usr/bin/env groovy

def call(body) {
  
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()
  
  def num1 = config.NUM1
  def num2   = config.NUM2
  echo "sum "+num1+num2
  echo "Hello, ${name}."
  

}

