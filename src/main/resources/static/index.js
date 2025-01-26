function validerVare() {
    const vare = $("#vare").val();
    const regexp = /^[A-Za-z0-9\s]{1,50}$/;
    if (regexp.test(vare)) {
        $("#feilVare").html("");
        return true;
    } else {
        $("#feilVare").html("Varen må være mellom 1 og 50 tegn og kun inneholde bokstaver, tall eller mellomrom.");
        return false;
    }
}

function leggTilVare() {
    const vare = { vare: $("#vare").val(), ok: false };
    if (validerVare()) {
        $.post("/lagreHandleliste", vare, function () {
            hentHandleliste();
            $("#vare").val("");
        }).fail(function (jqXHR) {
            $("#feil").html(jqXHR.responseText);
        });
    } else {
        $("#feil").html("Rett feilen før innsending.");
    }
}

function hentHandleliste() {
    $.get("/hentHandleliste", function (handlelister) {
        let ut = "";
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
        $("#utskrift").html(ut);
        if (handlelister.length === 0) {
            $("#utskrift").html('<li class="text-center text-muted" style="padding: 1rem;">Ingen varer i handlelisten</li>');
        }
    });
}

function oppdaterVare(id, ok) {
    const vare = { id: id, ok: ok };
    $.post("/oppdaterHandleliste", vare, function () {
        hentHandleliste();
    }).fail(function (jqXHR) {
        $("#feil").html(jqXHR.responseText);
    });
}

function slettVare(id) {
    $.post("/slettHandleliste", { id: id }, function () {
        hentHandleliste();
    }).fail(function (jqXHR) {
        $("#feil").html(jqXHR.responseText);
    });
}

$(function () {
    hentHandleliste();

    // Enter key support
    $("#vare").keypress(function(e) {
        if (e.which == 13) {
            leggTilVare();
        }
    });
});