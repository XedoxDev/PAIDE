package com.xedox.paide.utils;

import android.os.Bundle;

import androidx.annotation.NonNull;

import io.github.rosemoe.sora.lang.completion.SimpleCompletionItem;

import io.github.rosemoe.sora.lang.completion.CompletionHelper;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.util.MyCharacter;

import static com.xedox.paide.PAIDE.*;

public class TextMateLanguage extends io.github.rosemoe.sora.langs.textmate.TextMateLanguage {

    private String scope;

    public TextMateLanguage(String scopeName) {
        super(
                GrammarRegistry.getInstance().findGrammar(scopeName),
                GrammarRegistry.getInstance().findLanguageConfiguration(scopeName),
                GrammarRegistry.getInstance(),
                ThemeRegistry.getInstance(),
                true);
        scope = scopeName;
    }

    @Override
    public void requireAutoComplete(
            @NonNull ContentReference content,
            @NonNull CharPosition position,
            @NonNull CompletionPublisher publisher,
            @NonNull Bundle extraArguments) {
        super.requireAutoComplete(content, position, publisher, extraArguments);
        for (SnippetReader.Snippet s : SnippetReader.read(context, "processing")) {
            publisher.addItem(new SimpleCompletionItem(s.prefix, s.description, s.length, s.body));
        }
        CompletionHelper.computePrefix(content, position, MyCharacter::isJavaIdentifierPart);
    }
}
