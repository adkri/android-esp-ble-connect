import json
from markdown import markdown
from jinja2 import Template

# Read the JSON file
json_file = "session_log.json"
with open(json_file, "r") as file:
    data = json.load(file)

# Convert Markdown to HTML
for item in data["items"]:
    item["value"] = markdown(item["value"])

# HTML template
html_template = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{{ title }}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/4.0.0/github-markdown.min.css">
    <style>
        body {
            font-family: "Arial", sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .item {
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .item .from {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .item .value {
            font-size: 14px;
            line-height: 1.5;
        }

        .gpt {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
    <h1>{{ title }}</h1>
    <h2>{{ model }}</h2>
    {% for item in items %}
        <div class="item {% if item.from == 'gpt' %}gpt{% endif %}">
            <div class="from">{{ item.from }}</div>
            <div class="value">{{ item.value }}</div>
        </div>
    {% endfor %}
</body>
</html>
"""

# Render the template and write the output HTML
template = Template(html_template)
html_output = template.render(
    title=data["title"], model=data["model"], items=data["items"]
)

output_file = "session_log.html"
with open(output_file, "w") as file:
    file.write(html_output)

print(f"Generated HTML file: {output_file}")
