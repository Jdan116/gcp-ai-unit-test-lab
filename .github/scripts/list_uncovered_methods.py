#!/usr/bin/env python3
import os
import sys
import xml.etree.ElementTree as ET

if len(sys.argv) < 2:
    print("Usage: list_uncovered_methods.py <path-to-jacoco-xml>", file=sys.stderr)
    sys.exit(2)

xml_path = sys.argv[1]
repo = os.environ.get("GITHUB_REPOSITORY", "")
server = os.environ.get("GITHUB_SERVER_URL", "https://github.com")
sha = os.environ.get("LINK_SHA") or os.environ.get("GITHUB_SHA", "")
# Assume main src path; tweak if needed
SRC_ROOT = "src/main/java"

try:
    tree = ET.parse(xml_path)
    root = tree.getroot()
except Exception as e:
    print(f"Failed to parse XML at {xml_path}: {e}", file=sys.stderr)
    sys.exit(2)

uncovered = []

# JaCoCo structure:
# <report><package name="com/example/..."><class name="com/example/Foo" sourcefilename="Foo.java"><method name="bar" line="42" ...>...
for pkg in root.findall("package"):
    pkg_path = pkg.get("name", "")  # e.g., com/example/lab/service
    for clazz in pkg.findall("class"):
        class_path = clazz.get("name", "")       # e.g., com/example/lab/service/UserService
        source_file = clazz.get("sourcefilename")  # e.g., UserService.java
        # Build repo-relative path to source
        file_path = f"{SRC_ROOT}/{pkg_path}/{source_file}" if source_file else None

        for method in clazz.findall("method"):
            name = method.get("name", "")
            desc = method.get("desc", "")
            line = method.get("line")  # starting line of method (if available)

            # Determine coverage for the method by counters at method-level
            missed = covered = 0
            for counter in method.findall("counter"):
                if counter.get("type") == "INSTRUCTION":
                    missed += int(counter.get("missed", "0"))
                    covered += int(counter.get("covered", "0"))

            # Uncovered method: no executed instructions and at least some missed
            if covered == 0 and missed > 0:
                if file_path and line and repo and sha:
                    url = f"{server}/{repo}/blob/{sha}/{file_path}#L{line}"
                    display = f"{class_path.replace('/', '.')}.{name}{desc}"
                    uncovered.append((display, url))
                else:
                    display = f"{class_path.replace('/', '.')}.{name}{desc}"
                    uncovered.append((display, None))

print("### 🚨 Uncovered Methods\n")
if not uncovered:
    print("✅ All methods have some coverage!")
    sys.exit(0)

for display, url in uncovered:
    if url:
        print(f"- [{display}]({url})")
    else:
        print(f"- `{display}`")
