#!/usr/bin/python

from misaka import Markdown, HtmlRenderer
from subprocess import call

renderer = Markdown(HtmlRenderer())

dark_css = """<link href="http://netdna.bootstrapcdn.com/bootswatch/2.1.1/cyborg/bootstrap.min.css" rel="stylesheet">"""
light_css = """<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css" rel="stylesheet">"""

template = """
<!doctype html>
<html>
	<head>
	    <meta charset='utf - 8'>
	    <title>Stefan Lourens - Resume</title>
	    {0}
	</head>
	<body>
	<div style="width: 780px; margin:0 auto;">
		{1}
	</div>
	</body>
</html>
"""

def main():
	print "Reading markdown"
	file = open('resume.md', 'r')
	html = renderer(file.read())

	print "Converting to HTML"
	file = open('resume.html', 'w')
	file.write(template.format(light_css, html))
	file.flush()
	file.close()

	print "Converting to HTML"
	file = open('resume_dark.html', 'w')
	file.write(template.format(dark_css, html))
	file.flush()
	file.close()

	print "Converting to PDF"
	# wkhtmltopdf-0.11.0_rc1 does not exit properly
	call(["wkhtmltopdf", "resume.html", "resume.pdf"])
	

if __name__ == "__main__":
    main()
    