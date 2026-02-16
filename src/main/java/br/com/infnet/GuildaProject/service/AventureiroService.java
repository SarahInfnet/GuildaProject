package br.com.infnet.GuildaProject.service;

import br.com.infnet.GuildaProject.exception.EntityNotFoundException;
import br.com.infnet.GuildaProject.model.Aventureiro;
import br.com.infnet.GuildaProject.model.ClasseAventureiro;
import br.com.infnet.GuildaProject.model.Companheiro;
import br.com.infnet.GuildaProject.model.EspecieCompanheiro;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class AventureiroService {

    private final List<Aventureiro> aventureiros = new ArrayList<>();

    public AventureiroService() {
        Faker faker = new Faker();
        aventureiros.addAll(initDb(faker));

    }
    private List<Aventureiro> initDb(Faker faker){
        return LongStream.rangeClosed(1,100).mapToObj(
            id -> new Aventureiro(
                id,
                faker.name().fullName(),
                faker.options().option(ClasseAventureiro.values()),
                faker.number().numberBetween(1,101),
                faker.bool().bool(),
                faker.options().option(
                null,
                        new Companheiro(
                            faker.name().fullName(),
                            faker.options().option(EspecieCompanheiro.values()),
                            faker.number().numberBetween(0,101)
                        )
                )

            )
        ).toList();
    }

    public Aventureiro getById(Integer id){
        if(id < 1 || id > aventureiros.size()){
            throw new EntityNotFoundException("Recurso não encontrado");
        }
        return aventureiros.get(id - 1);
    }

    public Aventureiro create(String nome, ClasseAventureiro classe, Integer nivel){
        long ultimoId = (long) aventureiros.size();
        Aventureiro novoAventureiro = new Aventureiro( ultimoId + 1, nome, classe, nivel, true);
        aventureiros.addLast(novoAventureiro);
        return novoAventureiro;
    }

    public List<Aventureiro> listarTodos(){
        return aventureiros;
    }

    public List<Aventureiro> paginar(List<Aventureiro> base, int page, int size){
        int total = base.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        return base.subList(from,to);
    }

    public List<Aventureiro> filtrar(ClasseAventureiro classe, Boolean ativo, Integer nivel){

        return aventureiros.stream()
                .filter(a -> classe == null || a.getClasse() == classe)
                .filter(a -> ativo == null || a.getAtivo() == ativo)
                .filter(a -> nivel == null || a.getNivel() >= nivel).toList();

    }

    public Aventureiro encerrarVinculo(Integer id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setAtivo(false);
        return aventureiro;
    }

    public Aventureiro recrutarNovamente(Integer id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setAtivo(true);
        return aventureiro;
    }

    public Aventureiro atualizar(Integer id, String nome, ClasseAventureiro classe, Integer nivel) {
        Aventureiro aventureiro = getById(id);

        aventureiro.setNome(nome);
        aventureiro.setClasse(classe);
        aventureiro.setNivel(nivel);

        return aventureiro;
    }

    public Aventureiro atualizarCompanheiro(Integer id, String nome, EspecieCompanheiro especie, Integer lealdade) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setCompanheiro(new Companheiro(nome, especie, lealdade));
        return aventureiro;
    }

    public Aventureiro deletarCompanheiro(Integer id) {
        Aventureiro aventureiro = getById(id);
        aventureiro.setCompanheiro(null);
        return aventureiro;
    }
}
