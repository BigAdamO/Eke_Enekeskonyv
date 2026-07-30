#!/usr/bin/env python3

import json
import re

JSON_PATH = "/mnt/E/ProgramFiles/EKE/EKE_Enekeskonyv_App/Data/eke_enekek.json"
PUNCTUATION_PATTERN = re.compile(r'([,.;:!?])([^\s])')
CAPITAL_FOLLOWING_LOWER_PATTERN = re.compile(r'([a-z])([A-Z])')


def prettify_lyrics(text):
    if not isinstance(text, str):
        return text

    text = PUNCTUATION_PATTERN.sub(r'\1\n\2', text)
    return CAPITAL_FOLLOWING_LOWER_PATTERN.sub(r'\1\n\2', text)


def process_item(item):
    if isinstance(item, dict):
        return {key: process_item(value) for key, value in item.items()}
    if isinstance(item, list):
        return [process_item(value) for value in item]
    return prettify_lyrics(item)


def main():
    with open(JSON_PATH, "r", encoding="utf-8") as source:
        data = json.load(source)

    updated_data = process_item(data)

    if updated_data != data:
        with open(JSON_PATH, "w", encoding="utf-8") as target:
            json.dump(updated_data, target, ensure_ascii=False, indent=2)
        print(f"Updated {JSON_PATH}")
    else:
        print("No changes needed")


if __name__ == "__main__":
    main()
