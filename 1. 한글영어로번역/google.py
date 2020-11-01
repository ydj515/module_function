#-*- coding: utf-8 -*-
from googletrans import Translator
import constant
import csv

class Myopen(object):
    def __init__(self, fn, opening='', closing='', mode='r', encoding = 'utf-8', buffering=-1):
        if opening:
            print(opening)

        self.closing = closing
        self.mode = mode
        self.encoding = encoding
        self.f = open(fn, mode, buffering, encoding)

    def __enter__(self):
        return self.f

    def __exit__(self, exc_type, exc_value, traceback):
        self.f.close()
        if self.closing:
            print(self.closing)  


def main():
    translator = Translator()

    with Myopen(constant.PATH_READ_FILE, opening = constant.START_MESSAGE, closing = constant.END_MESSAGE, mode='r') as f:

        wf = open(constant.PATH_WRITE_FILE, 'wt', encoding = 'utf-8', newline="")
        lines = f.readlines()
        # print(lines)
        for i in lines:
            result = translator.translate(i, dest="en")
            
            
            print(type(result.text.split(',')))
            # print(constant.PRINT_SEPERATE_LINE)
            writer = csv.writer(wf)
            writer.writerow(result.text.split(','))


if __name__ == "__main__":
	main()

