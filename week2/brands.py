import os
import subprocess as sp
import Tkinter as tkinter

# return the remote brands
def brands():
	brands = os.listdir("/etc/lirc/remotes")
	brands = sorted(brands)
	print(brands)
	return brands

# return the files for a specific brand
def file(brand):
	files = os.listdir("/etc/lirc/remotes/" + brand)
	files = sorted(files)
	return files

# read the selected file for a brand
def readFile(filePath):
	fileLines = []
	with open(filePath) as my_file:
		fileLines = my_file.readlines()
	return fileLines

# parse the file read
## Could be combied with readFile for a little faster runtime however files so small 
### performace gain is minimal
def parseFile(lines):
	# boolean place holder to not start storage till we see the correct line
	start = False
	returnVal = []
	for i in lines:
		# if we see the end stop
		if "end remote" in i:
			start = False
			break
		# if we have found the start already
		if start == True:
			appendVal = []
			appendVal = i.split()
			# save to collector value
			returnVal.append(appendVal)
		# if we have not found the "begin codes" section
		if start == False:
			if "begin codes" in i:
				start = True
	return returnVal

def sendCommand(lineCommand):
#	REMOTE_PATH = "/etc/lirc/lircd.conf.d"
	sp.call(["irsend","SEND_ONCE", "Epson_12807990", lineCommand])

def drawStart(line):
	m = tkinter.Tk()
	m.title("Remote Control")
	i = 0
	PADDING = 10
	itmBrands = brands()
	line = parseFile(line)
	var = tkinter.StringVar(m)
	var.set("Choose One")
	
	tkinter.Button(m, text = 'power', command=lambda:sendCommand('KEY_POWER')).grid(row=0,column=0,columnspan=1,padx=PADDING)
	tkinter.Button(m, text = 'input', command=lambda:sendCommand('KEY_SEARCH')).grid(row=0,column=3,columnspan=1, padx=PADDING)
	tkinter.Button(m, text = 'Volume Up', command=lambda:sendCommand('KEY_VOLUMEUP')).grid(row=1,column=1,padx=PADDING, pady=PADDING)
	tkinter.Button(m, text = 'Volume Down',command=lambda:sendCommand('KEY_VOLUMEDOWN')).grid(row=2,column=1,padx=PADDING,pady=PADDING)
#	for r in range (GRID_MAX):
#		for c in range (GRID_MAX):
#			tkinter.Button(m, text = 'button').grid(row=r,column=c)
#	listbox = tkinter.Listbox(m)
#	listbox.pack()

#	listbox.insert(tkinter.END, "Please Select")

#	for itm in itmBrands:
#		listbox.insert(tkinter.END, itm)

#	button = tkinter.Button(m, text = 'Next', width=25, command=lambda: brands())
#	button.pack()
	m.mainloop()


def main():
#	allBrands = brands()
#	print(allBrands)
	allRemoteFiles = file("nokia")
#	print(allRemoteFiles)
	lines = readFile("/etc/lirc/remotes/nokia/DBOX2.lircd.conf")
#	print(parseFile(lines))
	drawStart(lines)

if __name__ == "__main__":
	main()

