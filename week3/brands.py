import os
import tkFont
import shutil
import subprocess as sp
import Tkinter as tkinter

# globals to address need for storing which is selected
remoteBrand = ""
remoteFile = ""
remoteLiteral = ""

# have to set buttons as global to keep images from garbage collector
powerButton = "" 
upButton    = "" 
downButton  = ""
leftButton  = ""
rightButton = ""
# return the remote brands

def brands():
    brands = os.listdir("/etc/lirc/remotes")
    brands = sorted(brands)
    print(brands)
    return brands

#move remote to correct config location
def moveRemoteToConfig(brand, file):
    source = "/etc/lirc/remotes/" + brand + "/" + file
    destination = "/etc/lirc/lircd.conf.d/" + file
    shutil.copyfile(source, destination)

# check to see if remote config file is already in destination
def checkFileExist(fileName):
    files = os.listdir("/etc/lirc/lircd.conf.d")
    files = sorted(files)
    found = False
    for file in files:
        if file == fileName:
            found = True
            break
    return found

# check to see if file exists in config and if not copy to config
def checkAndCopyIfNotExist(brand, file):
    if checkFileExist(file) == False:
        moveRemoteToConfig(brand,file)
        os.system("sudo /etc/init.d/lircd restart")

# return the files for a specific brand
def file(brand):
    files = [f for f in os.listdir("/etc/lirc/remotes/" + brand) if f.endswith('.conf')]
    files = sorted(files)
    t = []
    
    return files

# read the selected file for a brand
def readFile(filePath):
    fileLines = []
    with open(filePath) as my_file:
        fileLines = my_file.readlines()
    return fileLines


# parse the config file for the correct name
def parseName(file):
    returnVal = ""
    fileLines = readFile(file)

    for i in fileLines:
        if "name" in i:
            returnVal = i.split()[1] 
            return returnVal

#sends the physical command to the ir blaster
def sendCommand(lineCommand, remote):
#   REMOTE_PATH = "/etc/lirc/lircd.conf.d"
    sp.call(["irsend","SEND_ONCE", remote, lineCommand])


#opens the actual remote file
def openRemote(remoteFileName):
    global remoteLiteral
    global powerButton
    global upButton
    global downButton
    global rightButton
    global leftButton

    BUTTON_SIZE1= 50
    BUTTON_SIZE2= 30
    PADDING = 10
    
    remoteLiteral = parseName("/etc/lirc/lircd.conf.d/" + remoteFileName)

    m1 = tkinter.Toplevel()
    m1.title("Remote Control for " + remoteLiteral)

    tkinter.Button(m1, text = 'power', image=powerButton, compound=tkinter.TOP, width=BUTTON_SIZE1, height = BUTTON_SIZE1, command=lambda:sendCommand('KEY_POWER',remoteLiteral)).grid(row=0,column=0,columnspan=1,padx=PADDING)
    tkinter.Button(m1, text = 'input', command=lambda:sendCommand('KEY_SEARCH',remoteLiteral)).grid(row=0,column=4,columnspan=1, padx=PADDING)
    tkinter.Button(m1, text = '1',command=lambda:sendCommand('KEY_1',remoteLiteral)).grid(row=1,column=1)
    tkinter.Button(m1, text = '2',command=lambda:sendCommand('KEY_2',remoteLiteral)).grid(row=1,column=2)
    tkinter.Button(m1, text = '3',command=lambda:sendCommand('KEY_3',remoteLiteral)).grid(row=1,column=3)
    tkinter.Button(m1, text = '4',command=lambda:sendCommand('KEY_4',remoteLiteral)).grid(row=2,column=1)
    tkinter.Button(m1, text = '5',command=lambda:sendCommand('KEY_5',remoteLiteral)).grid(row=2,column=2)
    tkinter.Button(m1, text = '6',command=lambda:sendCommand('KEY_6',remoteLiteral)).grid(row=2,column=3)
    tkinter.Button(m1, text = '7',command=lambda:sendCommand('KEY_7',remoteLiteral)).grid(row=3,column=1)
    tkinter.Button(m1, text = '8',command=lambda:sendCommand('KEY_8',remoteLiteral)).grid(row=3,column=2)
    tkinter.Button(m1, text = '9',command=lambda:sendCommand('KEY_9',remoteLiteral)).grid(row=3,column=3)
    tkinter.Button(m1, text = '0',command=lambda:sendCommand('KEY_0',remoteLiteral)).grid(row=4,column=2)
    tkinter.Button(m1, text = 'Vol Up' ,image=upButton, compound=tkinter.TOP,width=BUTTON_SIZE1, height = BUTTON_SIZE1, command=lambda:sendCommand('KEY_VOLUMEUP',remoteLiteral)).grid(row=4,column=0,padx=PADDING, pady=PADDING)
    tkinter.Button(m1, text = 'Vol Down', image=downButton, compound=tkinter.TOP,width=BUTTON_SIZE1, height = BUTTON_SIZE1, command=lambda:sendCommand('KEY_VOLUMEDOWN',remoteLiteral)).grid(row=5,column=0,padx=PADDING,pady=PADDING)
    tkinter.Button(m1, text = 'Up', image=upButton, compound=tkinter.TOP, width=BUTTON_SIZE2 , height=BUTTON_SIZE2,command=lambda:sendCommand('KEY_UP',remoteLiteral)).grid(row=4,column=5)
    tkinter.Button(m1, text = 'Left', image=leftButton, compound=tkinter.TOP, width=BUTTON_SIZE2 , height=BUTTON_SIZE2,command=lambda:sendCommand('KEY_LEFT',remoteLiteral)).grid(row=5,column=4)
    tkinter.Button(m1, text = 'Right',image=rightButton, compound=tkinter.TOP, width=BUTTON_SIZE2 , height=BUTTON_SIZE2,command=lambda:sendCommand('KEY_RIGHT',remoteLiteral)).grid(row=5,column=6)
    tkinter.Button(m1, text = 'Down', image=downButton, compound=tkinter.TOP, width=BUTTON_SIZE2 , height=BUTTON_SIZE2,command=lambda:sendCommand('KEY_DOWN',remoteLiteral)).grid(row=6,column=5)
    tkinter.Button(m1, text = 'Enter', command=lambda:sendCommand('KEY_ENTER',remoteLiteral)).grid(row=5,column=5)
    tkinter.Button(m1, text = 'Menu', command=lambda:sendCommand('KEY_MENU',remoteLiteral)).grid(row=6,column=4)
    tkinter.Button(m1, text = 'Esc', command=lambda:sendCommand('KEY_ESC',remoteLiteral)).grid(row=6,column=6)

#happens when selected actual remote config file
def onselect1(evt):
    global remoteFile
    w = evt.widget
    index = int(w.curselection()[0])
    value = w.get(index)
    remoteFile = value
    print 'You selected item %d: "%s"' % (index, value)
    checkAndCopyIfNotExist(remoteBrand, remoteFile)
    openRemote(remoteFile)

#happens when selected brand
def onselect(evt):
    global remoteBrand
    w = evt.widget
    index = int(w.curselection()[0])
    value = w.get(index)
    remoteBrand = value
    print 'you selected: ' + value
    files = file(value)

    listbox = tkinter.Listbox()

    listbox.insert(tkinter.END, "Please Select")
    for itm in files:
        listbox.insert(tkinter.END, itm)
    listbox.bind('<<ListboxSelect>>',onselect1)
    listbox.grid(row=2,column=1)

#draw the start menu
def drawStart():
    global powerButton
    global upButton
    global downButton
    global leftButton
    global rightButton
    m = tkinter.Tk()
    m.title("Remote Control")
    i = 0
    PADDING = 10
    itmBrands = brands()
    var = tkinter.StringVar(m)
    var.set("Choose One")
    powerButton=tkinter.PhotoImage(file=r"/home/pi/Desktop/images/power.png")
    upButton=tkinter.PhotoImage(file=r"/home/pi/Desktop/images/up.png")
    downButton=tkinter.PhotoImage(file=r"/home/pi/Desktop/images/down.png")
    leftButton= tkinter.PhotoImage(file=r"/home/pi/Desktop/images/left.png")
    rightButton= tkinter.PhotoImage(file=r"/home/pi/Desktop/images/right.png")
    listbox = tkinter.Listbox()

    listbox.insert(tkinter.END, "Please Select")

    for itm in itmBrands:
        listbox.insert(tkinter.END, itm)
    listbox.bind('<<ListboxSelect>>',onselect)
    listbox.grid(row=1,column=1, padx=PADDING)  
    m.mainloop()


def main():
    drawStart()

if __name__ == "__main__":
    main()

