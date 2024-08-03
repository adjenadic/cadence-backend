import csv
import requests
import time
from datetime import datetime

# Constants
API_KEY = "2"
BASE_ARTIST_URL = f"https://www.theaudiodb.com/api/v1/json/{API_KEY}/artist.php?i="
BASE_ALBUM_URL = f"https://www.theaudiodb.com/api/v1/json/{API_KEY}/album.php?i="
START_ID = 111233
RATE_LIMIT = 0.5  # 2 calls per second
TOTAL_CALLS = 25000
OVERHEAD = 0.1  # Estimated overhead per call

# CSV files
ARTISTS_CSV = "artists.csv"
ALBUMS_CSV = "albums.csv"


# Function to append data to CSV files
def append_to_csv(filename, data):
    with open(filename, mode='a', newline='', encoding='utf-8') as file:
        writer = csv.writer(file, quoting=csv.QUOTE_MINIMAL)
        writer.writerow(data)


# Function to fetch and save artist data
def fetch_and_save_artist_data(start_id):
    url = BASE_ARTIST_URL + str(start_id)
    print(f"{datetime.now()} - Sending request to URL: {url}")
    response = requests.get(url)
    print(f"{datetime.now()} - Received response from URL: {url}")
    if response.status_code == 200:
        data = response.json().get('artists', [])
        if data:
            return data[0]
    else:
        print(f"Failed to fetch data for artist id {start_id}")
    return None


# Function to fetch and save album data
def fetch_and_save_album_data(artist_id):
    url = BASE_ALBUM_URL + str(artist_id)
    print(f"{datetime.now()} - Sending request to URL: {url}")
    response = requests.get(url)
    print(f"{datetime.now()} - Received response from URL: {url}")
    if response.status_code == 200:
        data = response.json().get('album', [])
        if data:
            return data
    else:
        print(f"Failed to fetch albums for artist id {artist_id}")
    return []


# Main script
if __name__ == "__main__":
    # Fetch a sample artist and album to determine headers
    sample_artist_data = fetch_and_save_artist_data(START_ID)
    sample_album_data = fetch_and_save_album_data(START_ID)

    if sample_artist_data:
        artist_headers = sample_artist_data.keys()
        with open(ARTISTS_CSV, mode='w', newline='', encoding='utf-8') as file:
            writer = csv.writer(file, quoting=csv.QUOTE_MINIMAL)
            writer.writerow(artist_headers)

    if sample_album_data:
        album_headers = sample_album_data[0].keys()
        with open(ALBUMS_CSV, mode='w', newline='', encoding='utf-8') as file:
            writer = csv.writer(file, quoting=csv.QUOTE_MINIMAL)
            writer.writerow(album_headers)

    # Fetch and save data
    for i in range(START_ID, START_ID + TOTAL_CALLS):
        artist_data = fetch_and_save_artist_data(i)
        if artist_data:
            append_to_csv(ARTISTS_CSV, artist_data.values())
        album_data = fetch_and_save_album_data(i)
        if album_data:
            for album in album_data:
                append_to_csv(ALBUMS_CSV, album.values())
        time.sleep(RATE_LIMIT + OVERHEAD)
