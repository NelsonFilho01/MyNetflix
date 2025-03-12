package br.com.rothmans_developments.mynetflix.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer temporada;

    private String titulo;

    private Integer numeroEpisodio;

    private Double avaliacao;

    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio() {}
    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio){
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();

        try{
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        }
        catch(NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try {
            String data = dadosEpisodio.dataLancamento();
            if (data != null && !data.equalsIgnoreCase("N/A")) {
                this.dataLancamento = LocalDate.parse(data);
            } else {
                this.dataLancamento = null;
            }
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }
    }

    // Get Set

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }


    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return  "temporada = " + temporada +
                ", titulo = '" + titulo + '\'' +
                ", numeroEpisodio = '" + numeroEpisodio +
                ", avaliacao = '" + avaliacao + '\'' +
                ", dataLancamento = " + dataLancamento + "\n";

    }
}
