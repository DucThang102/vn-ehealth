import os

fields = []

with open('fields.txt', encoding='utf-8') as f:
    for line in f:
        line = line.strip()
        if line.startswith('public'):
            pos = line.find(';')
            if pos > 0:
                line = line[:pos]
                pos = line.rfind(' ')
                if pos > 0:
                    fields.append(line[pos+1:])
                    
fields = list(set(fields))
print(fields)                    

for node, dirs, files in os.walk('.'):
    for fn in files:
        if fn.endswith('.jrxml'):
            with open(os.path.join(node, fn), encoding='utf-8') as f:
                content = f.read()
                
            content = content.replace(
'''
	<field name="phangui" class="java.lang.Integer">
		<fieldDescription><![CDATA[phangui]]></fieldDescription>
	</field>''', '')
                
            with open(os.path.join(node, fn), 'w', encoding='utf-8') as f:
                f.write(content)
                
            print('Done ', fn) 
                