
package com.github.gkttk.eight.xml.model.tariffs;

import com.github.gkttk.eight.xml.model.AbstractTariff;
import com.github.gkttk.eight.xml.model.InternetTariff;
import com.github.gkttk.eight.xml.model.SpeakTariff;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tariffs")

public class Tariffs {

    @XmlElements({
            @XmlElement(name = "internet-tariff", type = InternetTariff.class),
            @XmlElement(name = "speak-tariff", type = SpeakTariff.class)
    })
    private List<AbstractTariff> tariffs;


    public Tariffs() {
        this.tariffs = new ArrayList<>();
    }

    public List<AbstractTariff> getTariffs() {
        return tariffs;
    }


}
