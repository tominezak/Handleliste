function validerVare() {
    //Henter verdien fra input-feltet
    const vare = $("#vare").val();
    //1-50 tegn, kun bokstaver, tall og mellomrom
    const regexp = /^[A-Za-z0-9\s]{1,50}$/;

    //Sjekker om input verdien matcher regexp
    if (regexp.test(vare)) {
        //Fjerner feilmelding dersom input er gyldig
        $("#feilVare").html("");
        return true;
    } else {
        $("#feilVare").html("Varen må være mellom 1 og 50 tegn og kun inneholde bokstaver, tall eller mellomrom.");
        return false;
    }
}

function leggTilVare() {
    //Oppretter et vare-objekt med input-verdi og status "ok" satt til false
    const vare = { vare: $("#vare").val(), ok: false };
    if (validerVare()) {
        //Sender en POST-forespørsel til serveren for å lagre varen
        $.post("/lagreHandleliste", vare, function () {
            hentHandleliste(); //Henter oppdatert handleliste
            $("#vare").val(""); //Tømmer input-felt
        }).fail(function (jqXHR) {
            //Ved feil så vises feilmelding fra serveren
            $("#feil").html(jqXHR.responseText);
        });
    } else {
        $("#feil").html("Rett feilen før innsending.");
    }
}

function hentHandleliste() {
    //Gjør en GET-forespørsel til serveren for å hente lagrede varer
    $.get("/hentHandleliste", function (handlelister) {
        let ut = ""; //Tom streng for å bygge listen

        //Løper gjennom alle varer i handlelisten
        for (const h of handlelister) {
            ut += `
                <li>
                    <div class="item-content">
                        <input type="checkbox" class="checkbox" 
                               ${h.ok ? 'checked' : ''} 
                               onclick="oppdaterVare(${h.id}, ${!h.ok})">
                        <span class="${h.ok ? 'completed' : ''}">${h.vare}</span>
                    </div>
                    <button class="delete-btn" onclick="slettVare(${h.id})">
                        Slett
                    </button>
                </li>`;
        }
        //Oppdaterter utskrifts-innholdet
        $("#utskrift").html(ut);

        //Hvis handlelisten er tom vises melding til bruker
        if (handlelister.length === 0) {
            $("#utskrift").html('<li class="text-center text-muted" style="padding: 1rem;">Ingen varer i handlelisten</li>');
        }
    });
}

function oppdaterVare(id, ok) {
    //Oppretter et objekt med vare-ID og oppdatert status
    const vare = { id: id, ok: ok };

    //Sender en POST-forespørsel til serveren for å oppdatere varen
    $.post("/oppdaterHandleliste", vare, function () {
        //Henter oppdatert handleliste etter vellykket oppdatering
        hentHandleliste();
    }).fail(function (jqXHR) {
        $("#feil").html(jqXHR.responseText);
    });
}

function slettVare(id) {
    //Sender en POST-forespørsel til serveren for å slette varen
    $.post("/slettHandleliste", { id: id }, function () {
        //Henter oppdatert handleliste etter sletting
        hentHandleliste();
    }).fail(function (jqXHR) {
        $("#feil").html(jqXHR.responseText);
    });
}

//Utføres når dokumentet er ferdig lastet
$(function () {
    hentHandleliste();
    //Sjekker om brukeren trykker "Enter" (keycode 13)
    $("#vare").keypress(function(e) {
        if (e.which == 13) {
            //Kaller funksjonen for å legge til en vare
            leggTilVare();
        }
    });
});