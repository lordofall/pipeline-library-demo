#!/usr/bin/env groovy

def call(String name = 'human') {
  echo "Hello, ${name}."
  
  def num1           = config.NUM1
  def num2           = config.NUM2
  echo "sum "+num1+num2
}

