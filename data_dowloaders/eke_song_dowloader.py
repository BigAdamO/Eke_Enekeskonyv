from bs4 import BeautifulSoup
import requests
import json

print("EKE Énekeskönyv énekeinek letöltése megkezdődött... 🎶")

# URL, ahonnan az énekeket le akarjuk szedni

# url = "https://ekealapitvany.hu/enekeskonyv/001.html"

enekIdx = 1
songs = []

def getSong(url):
    
    # Weboldal letöltése
    r = requests.get(url)
    s = BeautifulSoup(r.content)

    complexTitle = s.find("p", class_="EnekCim").text

    id = complexTitle.split(".")[0].strip()
    title = complexTitle.split(".")[1].strip()


    versszakok = s.find_all("p", class_="Versszak")
    
    lyrics = ""
    x=1
    for versszak in versszakok:
        versszak = versszak.text.replace("<br/>", "\n").strip("\n")
        lyrics += str(x) + ". " + versszak + "\n\n"
        x+=1

    # lyrics = str(enek.select("p.szoveg")).replace("<br/>", "\n").replace("[<p class=\"szoveg\">", "").replace("</p>]", "").strip()

    song = {
        "id": id,
        "title": title,
        "lyrics": lyrics,
        "sheets": f"kotta_{id}"
    }

    return song


while enekIdx <= 463:
    url = f"https://ekealapitvany.hu/enekeskonyv/{str(enekIdx).zfill(3)}.html"
    song = getSong(url)
    songs.append(song)
    enekIdx += 1
    print(f"Letöltve: {enekIdx} ✅")




# JSON fájlba mentés
with open(r"/mnt/E/ProgramFiles/EKE/EKE_Enekeskonyv_App/Data/eke_enekek.json", "w", encoding="utf-8") as f:
    json.dump(songs, f, ensure_ascii=False, indent=4)

print("JSON fájl sikeresen elkészült! ✅")
