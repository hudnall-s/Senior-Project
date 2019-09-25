#SAM HUDNALL
#WEEK 1
#Code to make LED Blink

#Imports
import RPi.GPIO as GPIO
from time import sleep

#Set to see if pin is in use
GPIO.setwarnings(True)

#Set to board numbering mode
GPIO.setmode(GPIO.BOARD)

PIN = 12
SLEEP_TIME = 2
GPIO.setup(PIN,GPIO.OUT)

try:
    while True:
        print ("LED on")
        GPIO.output(PIN, GPIO.HIGH)
        sleep(SLEEP_TIME)
        print ("LED off")
        GPIO.output(PIN, GPIO.LOW)
        sleep(SLEEP_TIME)
except KeyboardInterrupt:
    print ("goodbye")
    GPIO.cleanup()
