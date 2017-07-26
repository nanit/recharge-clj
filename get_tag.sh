#!/bin/bash
cat project.clj | grep defproject | sed 's/(defproject recharge-clj "\(.*\)"/\1/g'
